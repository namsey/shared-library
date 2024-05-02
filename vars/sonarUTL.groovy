def call(httpUrl, username, password) {
def qualityGateProjectStatus = httpRequest(
url: httpUrl,
authentication: 'USERNAME_PASSWORD',
username: username,
password: password
).content
def qualityGate = new groovy.json.JsonSlurper().parseText(qualityGateProjectStatus)
if (qualityGate.status.condition.error) {
// Quality Gate failed, check if it's due to code coverage
if (
qualityGate.status.condition.name == 'Coverage') {
error "Code Coverage failed Quality Gate! Coverage below 60%"
} else {
// Quality Gate failed due to other reasons
error "SonarQube Quality Gate failed! See SonarQube for details"
}
}
// Your logic to check and fail based on Quality Gate status
}
