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

        stage('Make Task Definition') {
            steps {
                script {
                    def taskDefinitionVersion = params.VERSION.replaceAll(".", "_")
                    sh """
                    sed -i 's/image-version/${params.VERSION}'
                    sed -i 's/family-version/${taskDefinitionVersion}'

                    aws ecs register-task-definition --cli-input-json file://${env.WORKSPACE}/task-definition/zipsa-prod.json
                    """
                }
            }
        }

    }
}
