# eMoldino Terminal Software
eMoldino Terminal Software V3 Repository

## Software Version History
#### 3.1.0 Version
- Apply Spring Framework
- Apply OTA

#### 3.0.0 Version 
- Apply Logback Framework
- Apply Scenario
    - Fault Handling
    - Time Syncronization
    - Reset Command
    - Create
    - Install
    - Authenticate
    - Send Data
    - Heartbeat    
    
## Software Recommanded Specifications
- OS : Ubuntu 18.04 or later
- Memory : RAM 4GB or more
- Storage : SSD 100GB or more

## Install Software
#### 1. Copy the list of files below to the '/home/emoldino/Emoldino' directory.
 - EmoldinoTerminal.jar
 - startTM.sh
 - stopTM.sh
 - view_log.sh
 
#### 2. Change the permissions of files with the sh extension as follows.
```console
chmod +x /home/emoldino/Emoldino/*.sh
```
```console
chmod +x /home/emoldino/Emoldino/application.properties
```

#### 3. Turn off time synchronization settings. (The PC must be rebooted before it is applied normally)
```console
sudo timedatectl set-ntp 0
```

#### 4. Register Service

- Register services for running Terminal Software.
```console
sudo nano /etc/systemd/system/emoldinoTM.service
```

- Enter the setting value related to service execution as follows.
```
[Unit]
Description=Emoldino Terminal
After=network.target
[Install]
WantedBy=multi-user.target
[Service]
User=root
Group=root
PermissionsStartOnly=true
ExecStart=/bin/sh /home/emoldino/Emoldino/startTM.sh
ExecStop=/home/emoldino/Emoldino/stopTM.sh
#Service Executable File Settings
WorkingDirectory=/home/emoldino/Emoldino
TimeoutSec=600
# Restart Condition (in-failure : Restart if service on failure, always : always)
Restart=on-failure
#60 Second Cycle
RestartSec=60
RuntimeDirectoryMode=755
```

- Activate Service
```console
sudo systemctl daemon-reload 
```
```console
sudo systemctl enable emoldinoTM.service
```

#### 5. Start Service
```console
sudo systemctl start emoldinoTM.service
```

#### 6. Stop Service
```console
sudo systemctl stop emoldinoTM.service
```

#### 7. View Terminal Software Log
- If you want to view the real-time log for the service, check it by executing the command below.
```console
sudo sh /home/emoldino/Emoldino/view_log.sh
```

## Upgrade Software
#### 1. Stop Service
```console
sudo systemctl stop emoldinoTM.service
```

#### 2. Change File EmoldinoTerminal.jar 
- File Directory 
```console
ls /home/emoldino/Emoldino
```

#### 3. Start Service
```console
sudo systemctl start emoldinoTM.service
```
