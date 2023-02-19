def taskDefinitionVersion

pipeline {
    agent any

    environment {
        SHELL_DIR = "shell-script"
        SERVICE_DIR = "service"
        TASK_DIR = "task"
        TASK_DEFINITION_DIR = "task-definition"
    }

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['prod', 'dev'], description: '')
        string(name: 'VERSION', defaultValue: '', description: '')
    }

    stages {

        stage('Init') {
            steps {
                script {
                    taskDefinitionVersion = params.VERSION.replaceAll("\\.", "_")
                    sh """
                    sed -i 's/image-version/${params.VERSION}/g' task-definition/zipsa-prod.json
                    sed -i 's/family-version/${taskDefinitionVersion}/g' task-definition/zipsa-prod.json

                    sed -i 's/family-version/${taskDefinitionVersion}/g' service-definition/zipsa-prod.json
                    """
                }
            }
        }

        stage('Create Task Definition') {
            steps {
                script {
                    taskDefinitionVersion = params.VERSION.replaceAll("\\.", "_")
                    sh """
                    aws ecs register-task-definition --cli-input-json file://${env.WORKSPACE}/task-definition/zipsa-prod.json
                    """
                }
            }
        }

        stage('Create Service') {
            steps {
                script {
                    sh """
                    aws ecs create-service \
                    --cluster zipsa-prod \
                    --service-name zipsa-prod \
                    --cli-input-json file://${env.WORKSPACE}/service-definition/zipsa-prod.json
                    """
                }
            }
        }

    }
}
