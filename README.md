# Azure POS

This application sends a payload to Azure IoT Hub.

## Setup

1. Create an Azure IoT hub tenant

3. Go to 'Shared access policies' and select the policy 'iothubowner'

2. Copy to clipboard `Connection string - primary key`

3. Create file `key.properties` in the project directory

4. Paste the key inside quotation marks
```
AzureIotHubConnectionKey="connectionString"
```