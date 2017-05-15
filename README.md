Workshop-hystrix
================

# Exercice 7:

## Problem

This Exercice shows how can we protected our apps from a 3rd-party system that becomes too slow.

In this example, all MyApp Server's threads will be used to handle calls to the first server.
This situation lead to a read-timeout for all calls to the second remote server.

![Alt text](docs/images/hystrix_threadpool.png)

