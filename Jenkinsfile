pipeline {
  agent any
  stages {
    stage('Pull code from Github') {
      dir('test') {
        git branch: 'develop', changelog: false, poll: false, url: 'https://github.com/Dasi-Rakesh/cucumber-java-parallel'
      }
    }

    stage('Run Test') {
      browserstack(credentialsId: "${params.BROWSERSTACK_USERNAME}") {
        def user = "${env.BROWSERSTACK_USERNAME}"
        if (user.contains('-')) {
          user = user.substring(0, user.lastIndexOf('-'))
        }
        withEnv(['BROWSERSTACK_USERNAME=' + user]) {
          sh label: '', returnStatus: true, script: ''
          '#!/bin/bash -l
          mvn clean test - DsuiteXmlFile = testng.xml ''
          '
        }
      }
    }
  }
}