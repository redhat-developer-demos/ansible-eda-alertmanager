FROM registry.access.redhat.com/ubi9-minimal
RUN microdnf install java-17 python3 gcc python3-devel -y && microdnf clean all && python -m ensurepip --upgrade && pip3 install ansible ansible-rulebook asyncio aiokafka aiohttp aiosignal kubernetes requests
ENV JAVA_HOME="/usr/lib/jvm/jre-17"
RUN mkdir /eda-ansible

COPY first-kubernetes-playbook.yaml /eda-ansible/first-kubernetes-playbook.yaml

RUN ansible-galaxy collection install ansible.eda kubernetes.core sabre1041.eda
WORKDIR /eda-ansible

EXPOSE 5001

CMD ansible-playbook first-kubernetes-playbook.yaml