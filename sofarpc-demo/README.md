## Steps to start SOFARPC test demo
- Step 1: You can compile sofarpc-api, sofarpc-client, sofarpc-server modules.
  - Will generate the generator grpc to define the service interface
- Step 2: You must start a Zookeeper service and should be used for service discovery for the project to start dependent on the ZooKeeper service
- Step 3: Run sofarpc-server and sofarpc-client applications
- Step 4: You can use the `sofarpc.http` file I provided to send http messages,but you have to use the httpclient that comes with the idea to execute the file[v2free.yaml](..%2F..%2F..%2F..%2F.config%2Fclash%2Fv2free.yaml)