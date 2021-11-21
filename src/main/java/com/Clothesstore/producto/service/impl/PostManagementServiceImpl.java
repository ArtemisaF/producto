package com.Clothesstore.producto.service.impl;

import com.Clothesstore.producto.client.firebase.FirebaseInitializer;
import com.Clothesstore.producto.client.firebase.StorageInitializer;
import com.Clothesstore.producto.dto.ProductosDto;
import com.Clothesstore.producto.service.PostManagementService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PostManagementServiceImpl implements PostManagementService {

    @Autowired
    private FirebaseInitializer firebase;
    @Autowired
    private StorageInitializer storageInitializer;

    @Override
    public String add(ProductosDto post) throws ExecutionException, InterruptedException {
        Map<String, Object> docData = getDocData(post);
        if((post.getPais().toLowerCase().contentEquals("colombia"))|| (post.getPais().toLowerCase().contentEquals("mexico"))){
            if(post.getPorcentajeDescuento()<=50){
                ApiFuture<DocumentReference> addedDocRef = getCollection().add(docData);
                String id = addedDocRef.get().getId();
                try {
                    if (null != addedDocRef.get()) {
                        return id;
                    }
                    return "Ha ocurrido un error";
                } catch (Exception e) {
                    return "Ha ocurrido un error";
                }
            }else {
                return "No debe superar el 50% en estos paises(Colombia, Mexico)";
            }
        }else {
            if(post.getPorcentajeDescuento()<=30){
                ApiFuture<DocumentReference> addedDocRef = getCollection().add(docData);
                String id = addedDocRef.get().getId();
                try {
                    if (null != addedDocRef.get()) {
                        return id;
                    }
                    return "Ha ocurrido un error";
                } catch (Exception e) {
                    return "Ha ocurrido un error";
                }
            }else {
                return "No debe superar el 30% en estos paises(Chile, Peru)";
            }
        }

    }

    @Override
    public List<String>  addImagen(String id,MultipartFile frontal,MultipartFile trasera) throws IOException {
        List<String> files = new LinkedList<>();
        files.add(uploadFile(frontal));
        files.add(uploadFile(trasera));
        ApiFuture<WriteResult> writeResultApiFuture= getCollection().document(id).update("imagen",files);
        try {
            if (null != writeResultApiFuture.get()) {
               return files;
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public ProductosDto getById(String id) {
        DocumentReference doc= getCollection().document(id);
        ApiFuture<DocumentSnapshot> future = doc.get();
        ProductosDto res = new ProductosDto();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                res = document.toObject(ProductosDto.class);
                res.setNumeroDeBusquedas(res.getNumeroDeBusquedas()+1);
                ApiFuture<WriteResult> writeResultApiFuture= getCollection().document(id).update("numeroDeBusquedas",res.getNumeroDeBusquedas());
                return res;
            } else {
                return null;
            }
        } catch (Exception e) {
            return res;


        }
    }

    @Override
    public List<ProductosDto> getMoreSearch() {
        List<ProductosDto> response= new ArrayList<>();


        ApiFuture<QuerySnapshot> querySnapshotApiFuture= getCollection().whereGreaterThan("numeroDeBusquedas",0).orderBy("numeroDeBusquedas", Query.Direction.DESCENDING).get();
        try {
            List<QueryDocumentSnapshot> documents = querySnapshotApiFuture.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                ProductosDto post = document.toObject(ProductosDto.class);
                response.add(post);
            }
            return  response;
        } catch (Exception e) {
            return null;
        }
    }




    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("Productos");
    }



    private Map<String, Object> getDocData(ProductosDto post)  {
        Map<String,Object> docData= new HashMap<>();
        docData.put("nombre", post.getNombre());
        docData.put("descripcion", post.getDescripcion());
        docData.put("precio", post.getPrecio());
        docData.put("porcentajeDescuento", post.getPorcentajeDescuento());
        docData.put("imagen", post.getImagen());
        docData.put("pais", post.getPais());
        docData.put("numeroDeBusquedas", 0);
        return docData;
    }

    private String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();
        if(Files.size(filePath)>1048576){
            file= rescale(multipartFile);
            filePath=file.toPath();
        }
        String objectName = generateFileName(multipartFile);

        Storage storage = storageInitializer.getStorageOptions().getService();
        BlobId blobId = BlobId.of("clothesstore-7bfe9.appspot.com", objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, Files.readAllBytes(filePath));

        return objectName;
    }


    private File rescale(MultipartFile multipartFile) throws IOException {

        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();
        BufferedImage bf = null;
        bf = ImageIO.read(new File(filePath.toString()));

        int ancho = bf.getWidth();
        int alto = bf.getHeight();
        int escalaAncho = (int)(0.3* ancho);
        int escalaAlto = (int)(0.3*alto);
        BufferedImage bufim = new BufferedImage(escalaAncho, escalaAlto, bf.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bf, 0,0, escalaAncho,escalaAlto, 0,0,ancho,alto, null);
        g.dispose();

        File outputfile = new File(filePath.toString());
        ImageIO.write(bufim, "jpg", outputfile);

        return outputfile;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }


}
