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
        choice(name: 'SERVICE', choices: ['zipsa'], description: '')
        string(name: 'TAG_NAME', defaultValue: '', description: '')
    }

    stages {

        stage('Init') {
            steps {
                script {
                    sh """
                    sed -i 's/tag-name/${params.TAG_NAME}/g' task-definition/zipsa-prod.json
                    sed -i 's/family-name/${params.SERVICE}/g' task-definition/zipsa-prod.json
                    sed -i 's/family-name/${params.SERVICE}/g' service-definition/zipsa-prod.json
                    """
                }
            }
        }

        stage('Create Task Definition') {
            steps {
                script {
                    sh """
                    aws ecs register-task-definition --cli-input-json file://${env.WORKSPACE}/task-definition/${params.SERVICE}-${params.ENVIRONMENT}.json
                    """
                }
            }
        }

        stage('Create Service') {
            steps {
                script {
                    sh """
                    aws ecs create-service \
                    --cluster ${params.SERVICE}-${params.ENVIRONMENT} \
                    --service-name ${params.SERVICE}-${params.ENVIRONMENT} \
                    --cli-input-json file://${env.WORKSPACE}/service-definition/${params.SERVICE}-${params.ENVIRONMENT}.json
                    """
                }
            }
        }

    }
}
