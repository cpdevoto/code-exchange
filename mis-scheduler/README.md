# mis-scheduler
The **mis-scheduler** application is a standalone Java service built on the **[homonculus](https://github.com/doradosystemsadmin/mis-common/tree/master/homonculus-core)** framework.  The application is designed to generate tasks at configurable time intervals, and to post these tasks to a distributed [RabbitMq](https://www.rabbitmq.com/) queue so that they can be executed by a cluster of worker processes. As such, the **mis-scheduler** application serves as the root node of a distributed queues-and-workers architecture designed for horizontal scalability by adding more worker processes to accommodate additional loads.     

Currently, the **mis-scheduler** application supports a single scheduled ***claims retrieval*** job.  This job retrieves the set of all operators together with the set of all npi's associated with each operator. The job then generates one task per operator, including the set of all npi's associated with that operator.  All configurations for the application are stored within the ```config/mis-scheduler.yml``` file.

Job recurrence rules are defined within the ```mis-scheduler.yml``` file using cron syntax (see the **[Cron Trigger Tutorial](http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html)**): 

```yaml
jobs:
  # A list of jobs to be executed by the scheduler, each including a cron recurrence rule
  - name: claim-retrieval
    recurrenceRule: 0 0/1 * * * ? # fire the claim-retrieval job once per minute
```
The application is designed to make it very easy to add new scheduled jobs.
