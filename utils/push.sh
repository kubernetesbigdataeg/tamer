./mvnw clean package \
    -Dquarkus.container-image.build=true \
    -Dquarkus.container-image.name=kubernetesbigdataeg/tamer
    -Dquarkus.jib.base-jvm-image=ubi8/openjdk-11-runtime:1.14
