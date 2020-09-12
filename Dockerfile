FROM ubuntu:latest

MAINTAINER "Sivaganesh Panditi <ganesh0479@gmail.com>"
RUN  apt-get update \
  && apt-get install -y wget \
  && rm -rf /var/lib/apt/lists/*

RUN apt-get update && apt-get install -y git

RUN wget --no-verbose -O /tmp/OpenJDK14U-jdk_x64_linux_hotspot_14.0.2_12.tar.gz https://api.adoptopenjdk.net/v3/binary/latest/14/ga/linux/x64/jdk/hotspot/normal/adoptopenjdk?project=jdk
RUN tar xzf /tmp/OpenJDK14U-jdk_x64_linux_hotspot_14.0.2_12.tar.gz -C /opt/
RUN ln -s /opt/jdk-14.0.2+12 /opt/JDK_14
RUN ln -s /opt/JDK_14/bin/java /usr/local/bin
RUN rm -f /tmp/OpenJDK14U-jdk_x64_linux_hotspot_14.0.2_12.tar.gz
ENV JAVA_HOME /opt/JDK_14

### maven : begin

RUN wget --no-verbose -O /tmp/apache-maven-3.3.3.tar.gz http://archive.apache.org/dist/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz
RUN tar xzf /tmp/apache-maven-3.3.3.tar.gz -C /opt/
RUN ln -s /opt/apache-maven-3.3.3 /opt/maven
RUN ln -s /opt/maven/bin/mvn /usr/local/bin
RUN rm -f /tmp/apache-maven-3.3.3.tar.gz
ENV MAVEN_HOME /opt/maven

### maven : end

# set the path of the working dir
RUN mkdir /usr/src/go-tiny
WORKDIR /usr/src/go-tiny

# clone the repository with the code
RUN git clone -b master git://github.com/ganesh0479/go-tiny.git

WORKDIR /usr/src/go-tiny/go-tiny
# install npm modules
RUN mvn package spring-boot:repackage -DskipTests=true
RUN chmod 777 /tmp

RUN yes | cp -rf /usr/src/go-tiny/go-tiny/go-tiny-service/target/go-tiny-service-0.0.1-SNAPSHOT-spring-boot.jar /usr/src/go-tiny

CMD ["java", "-jar", "/usr/src/go-tiny/go-tiny-service-0.0.1-SNAPSHOT-spring-boot.jar"]