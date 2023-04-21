pipeline {
   agent any
   stages {
      stage('setup') {
         steps {
            browserstack(credentialsId: 'caf7c234-7358-44ab-955a-6040f843ccb6') {
               echo "hello"
            }
         }
      }
    }
  }
