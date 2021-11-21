package com.Clothesstore.producto.client.firebase;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializer {

    @Value("${credencials.database_Url}")
    private String dataUrl;
    @PostConstruct
    private void IniFirestore() throws IOException {

        InputStream serviceAccount =getClass().getClassLoader().getResourceAsStream("privite-firebase.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(dataUrl)
                .build();

        if (FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }

    }
    public Firestore getFirestore(){
        return FirestoreClient.getFirestore();
    }
}
