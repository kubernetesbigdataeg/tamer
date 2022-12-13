mvn package -DskipTests (for production)
mvn package -DskipTests -Dskip.npm (for front development, the UI component is not necessary)

podman build -f src/main/docker/Dockerfile.jvm -t docker.io/kubernetesbigdataeg/tamer .
podman tag docker.io/kubernetesbigdataeg/tamer:latest docker.io/kubernetesbigdataeg/tamer:1.0.0-SNAPSHOT

#podman run -i --rm -p 8080:8080 -p 5005:5005 -e MOCK="true" -e JAVA_ENABLE_DEBUG="true" docker.io/kubernetesbigdataeg/tamer:1.0.0-SNAPSHOT

podman login docker.io -u kubernetesbigdataeg
podman image push docker.io/kubernetesbigdataeg/tamer:1.0.0-SNAPSHOT
