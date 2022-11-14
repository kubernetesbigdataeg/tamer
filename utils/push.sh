#KUBECONFIG=/home/javiroman/.kube/vagrant ./mvnw clean package \
#    -Dquarkus.container-image.build=true \
#    -Dquarkus.container-image.name=kubernetesbigdataeg/tamer
#    -Dquarkus.jib.base-jvm-image=ubi8/openjdk-11-runtime:1.14

export QUARKUS_CONTAINER_IMAGE_USERNAME=kubernetesbigdataeg
export QUARKUS_CONTAINER_IMAGE_PASSWORD=$MYPASSWORD
KUBECONFIG=/home/javiroman/.kube/vagrant ./mvnw install \
  -Dquarkus.container-image.registry=docker.io \
  -Dquarkus.container-image.group=kubernetesbigdataeg \
  -Dquarkus.container-image.push=true \
  -Dquarkus.kubernetes.deploy=true

# Manual deploy
k apply -f target/kubernetes/kubernetes.yml