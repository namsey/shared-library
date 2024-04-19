def call () {
  scannerHome = tool 'sonar-scanner'
     }
       withSonarQubeEnv('Sonarqube Server') {
          sh "${scannerHome}/bin/sonar-scanner"
            }
     }
}
