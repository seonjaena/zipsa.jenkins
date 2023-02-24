#!/bin/groovy

import jenkins.model.Jenkins
import groovy.transform.Field

@Field String jsonDir = "json"
@Field String ws

def executeCommand(String command) {
    ws = env.WORKSPACE
    sh(script: command)
}

def init(String tagName) {
    executeCommand("sed -i 's/tag-name/${tagName}/g' ${ws}/${jsonDir}/register-task-definition/zipsa-prod.json")
}

def registerTaskDefinition(String service, String environment) {
    executeCommand("aws ecs register-task-definition --cli-input-json file://${ws}/${jsonDir}/register-task-definition/${service}-${environment}.json")
}

def createService(String service, String environment) {
    executeCommand("aws ecs create-service --cli-input-json file://${ws}/${jsonDir}/create-service/${service}-${environment}.json")
}

def rollingUpdate(String service, String environment) {
    executeCommand("aws ecs update-service --service ${service}-${environment} --cli-input-json file://${ws}/${jsonDir}/update-service/rolling/${service}-${environment}.json")
}

def canaryUpdate(String service, String environment) {
    executeCommand("aws ecs update-service --service ${service}-${environment} --cli-input-json file://${ws}/${jsonDir}/update-service/canary/${service}-${environment}.json")
}