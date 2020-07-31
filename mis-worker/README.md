# mis-worker
The **mis-worker** application is a standalone Java service built on the **[homonculus](https://github.com/doradosystemsadmin/mis-common/tree/master/homonculus-core)** framework.  The application is designed to pull tasks from a distributed [RabbitMq](https://www.rabbitmq.com/) queue and to execute those tasks. As such, the **mis-worker** application serves as part of a distributed queues-and-workers architecture designed for horizontal scalability by adding more worker processes to accommodate additional loads.     

Currently, the **mis-worker** application supports the execution of a single type of task: a ***claims retrieval*** task.  When such a task appears on the queue, one instance of the **mis-worker** application will receive a message containing information about a single operator and all of the npi's associated with that operator.  For each npi, the **mis-worker** application will invoke a shell script that interfaces to the DDE system in order to retrieve the set of all claims associated with that operator/npi combination. For each claim retrieved, the **mis-worker** application will make the corresponding updates to the MIS database.

All configurations for the application are stored within the ```config/mis-worker.yml``` file.

Details about which processes to invoke for each different type of task are configured as part of the DDE service object: 

```yaml
ddeService:
  # the maximum amount of time that a given service call will wait before it times out and throws an exception
  serviceTimeout: 1 minute

  # A list of named shell scripts together with the absolute or relative path to the corresponding executable (paths are relative to the mis-worker/bin directory).
  processPaths:
    retrieveClaims: './retrieve-claims.sh'  
```
The application is designed to make it very easy to add new types of tasks.
