def call(gateName) {
// Access SonarQube server details from Jenkins credentials
def sonarqubeUrl = credentials('SONARQUBE_SERVER_URL')
def sonarqubeUsername = credentials('SONARQUBE_USERNAME')
def sonarqubePassword = credentials('SONARQUBE_PASSWORD')
// Define the SonarQube API endpoint for quality gate status
def apiUrl = "${sonarqubeUrl}/api/qualitygates/project_status?projectKey=\${JOB_NAME}&gateId=\${gateName}"
// Use 'sh' step to execute curl command and capture response
def response = sh(
script: "curl -u ${sonarqubeUsername}:${sonarqubePassword} ${apiUrl}",
returnStdout: true
)
// Parse the JSON response to get the quality gate status
def jsonSlurper = new JsonSlurper()
def data = jsonSlurper.parseText(response)
// Check if the quality gate is not met (status != OK)
if (data.status != 'OK') {
error "SonarQube Quality Gate '${gateName}' failed for project '${JOB_NAME}'"
}
// Success if quality gate is met
echo "SonarQube Quality Gate '${gateName}' passed for project '${JOB_NAME}'"
}
