FROM registry.access.redhat.com/ubi9-minimal
RUN microdnf install java-17 python3 gcc python3-devel -y && microdnf clean all && python -m ensurepip --upgrade && pip3 install ansible ansible-rulebook asyncio aiokafka aiohttp aiosignal kubernetes requests ansible-runner wheel
ENV JAVA_HOME="/usr/lib/jvm/jre-17"
RUN mkdir /eda-ansible

RUN ansible-galaxy collection install community.general ansible.eda kubernetes.core sabre1041.eda

COPY do-something.yml /eda-ansible/do-something.yml
COPY inventory.yml /eda-ansible/inventory.yml
COPY alertmanager-rule.yml /eda-ansible/alertmanager-rule.yml

WORKDIR /eda-ansible
EXPOSE 5001 8080

CMD ["ansible-rulebook", "--rulebook", "alertmanager-rule.yml", "-i", "inventory.yml", "--verbose", "--print-events"]