# This is ID number creator. Create ARC number for testing purpose.
## SQL Server
```
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=SqlServerTmnewa8F" -p 1433:1433 --name SqlServer2019 -d mcr.microsoft.com/mssql/server:2019-CU3-ubuntu-18.04
```

## Docker
### 建立 Docker 映像檔

```
docker build --no-cache -f ./config/Dockerfile -t local/idnocreator:latest .
```
- --no-cache：不使用快取，強制重新建立映像檔。
- -f：指定 Dockerfile 的路徑。
- -t：指定映像檔的名稱和標籤（此處為 local/idnocreator:latest）。

### 執行 Docker 容器
```
docker run -d -p 8080:8080 --name idno-creator-container local/idnocreator:latest
```
- -d：以分離模式運行容器。
- -p：將本機埠映射到容器埠（此處為 8080:8080）。
- --name：指定容器的名稱（此處為 idno-creator-container）。

### 將 Docker 映像檔標記為 Azure 容器註冊表的映像
```
docker tag local/idnocreator:latest ecrd1cr.azurecr.io/test/adan/idnocreator:latest
```
- local/idnocreator:latest：本地映像檔的名稱和標籤。
- ecrd1cr.azurecr.io/test/adan/idnocreator:latest：目標映像檔的名稱和標籤。

### 登入 Azure
```
az login --tenant ********-****-****-****-************
```
- --tenant：指定 Azure 的租戶 ID。

### 登入 Azure 容器註冊表 (ACR)
```
az acr login --name ecrd1cr
```
- --name：指定 Azure 容器註冊表的名稱（此處為 ecrd1cr）。

### 推送映像檔到 Azure 容器註冊表
```
docker push ecrd1cr.azurecr.io/test/adan/idnocreator:latest
```
- ecrd1cr.azurecr.io/test/adan/idnocreator:latest：要推送的映像檔名稱和標籤。
