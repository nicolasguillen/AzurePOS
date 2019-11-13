# Azure POS

This application sends a payload to Azure IoT Hub.

## Setup

1. Create an Azure IoT hub tenant.

2. Go to 'IoT devices' and create a new one.

3. Press on the created device and copy to clipboard `Connection string - primary key`.

4. Create file `key.properties` in the Android project directory.

5. Paste the key inside quotation marks.
```
AzureIotHubConnectionKey="connectionString"
```
