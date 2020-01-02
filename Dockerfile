FROM openjdk:8
VOLUME /tmp
ADD ./target/savings-account-ms-0.0.1-SNAPSHOT.jar savings-account-ms.jar
ENTRYPOINT ["java","-jar","/savings-account-ms.jar"]