package com.Clothesstore.producto.client.firebase;


import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class StorageInitializer {

    private StorageOptions storageOptions;

    @Value("clothesstore-7bfe9.appspot.com")
    private String storageName;
    private void IniFirestore() throws IOException {

        InputStream serviceAccount =getClass().getClassLoader().getResourceAsStream("privite-firebase.json");

        this.storageOptions= StorageOptions.newBuilder()
                .setProjectId("clothesstore-7bfe9")
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

    }

    public StorageOptions getStorageOptions() throws IOException {
        IniFirestore();
        return this.storageOptions;
    }

}