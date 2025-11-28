#建立MAVEN環境 進行專案編譯

## 建立MAVEN環境
FROM nexus-s.tmnewa.com.tw/ecrd1-docker-group/library/maven:3.9.11-amazoncorretto-21 AS build
## 設定工作目錄
WORKDIR /app
## 複製專案檔案
COPY ../pom.xml .
COPY ../src ./src
## 複製maven設定檔
COPY ../config/settings.xml /root/.m2/settings.xml
## 使用設定檔進行編譯專案 若有編譯錯誤則回覆錯誤內容

RUN mvn --settings /root/.m2/settings.xml clean package

# 建立JDK環境 進行專案執行
## 建立JDK環境(包含ImageMagick)
FROM nexus-s.tmnewa.com.tw/ecrd1-docker-group/library/openjdk:21-slim AS runtime
## 轉出PORT
EXPOSE 8080
## 複製編譯好的jar檔案
COPY --from=build /app/target/IDNOcreator.jar IDNOcreator.jar
## 執行jar檔案
ENTRYPOINT ["java", "-jar", "IDNOcreator.jar"]
