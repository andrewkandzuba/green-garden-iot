# green-garden-iot
Green Garden IoT Services

## Publishing to Google Cloud Platform
### Initializing/Setting the Project within gcloud
* Either set the project
```gcloud config set project <project name```
* Or else, execute interactive initialization via
```gcloud init```


## Configuring google pub/sub emulator
* Configure the application to use the emulator by adding this line to the application.properties ``` spring.cloud.gcp.pubsub.emulatorHost=localhost:8085 ```
* Start the emulator ``` gcloud beta emulators pubsub start ```

