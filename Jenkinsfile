pipeline {
  agent any

  environment {
    DOCKERHUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIAL')
    VERSION = "${env.BUILD_ID}"
  }

  tools {
    maven "Maven"
  }

  stages {

    stage('Checkout Source') {
      steps {
        checkout scm
      }
    }

    stage('Maven Build') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }

    stage('Run Tests') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Docker Build and Push') {
      steps {
        sh '''
          echo $DOCKERHUB_CREDENTIALS_PSW | docker login \
          -u $DOCKERHUB_CREDENTIALS_USR --password-stdin

          docker build -t pravin2110/restaurant-listing-service:${VERSION} .
          docker push pravin2110/restaurant-listing-service:${VERSION}
        '''
      }
    }

    stage('Update Image Tag in GitOps') {
      steps {
        checkout scmGit(
          branches: [[name: '*/master']],
          userRemoteConfigs: [[
            credentialsId: 'git-ssh',
            url: 'git@github.com:udemy-dev-withK8s-AWS-codedecode/deployment-folder.git'
          ]]
        )

        script {
          sh '''
            sed -i "s|image:.*|image: pravin2110/restaurant-listing-service:${VERSION}|" aws/restaurant-manifest.yml
            git add aws/restaurant-manifest.yml
            git commit -m "Update image tag to ${VERSION}"
          '''
          sshagent(['git-ssh']) {
            sh 'git push origin master'
          }
        }
      }
    }

    stage('Cleanup Workspace') {
      steps {
        deleteDir()
      }
    }
  }
}
