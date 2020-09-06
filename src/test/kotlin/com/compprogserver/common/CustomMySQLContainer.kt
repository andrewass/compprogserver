package com.compprogserver.common

import org.testcontainers.containers.MySQLContainer

class CustomMySQLContainer(imageName: String = "mysql:8.0") : MySQLContainer<CustomMySQLContainer>(imageName)