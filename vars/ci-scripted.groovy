
def call () {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts=""
    }
    node('workstation') {

      try {

        stage('check out code') {
           sh 'ls -l'
           cleanws()
           sh 'ls -l'
           git branch: 'main', url: 'http;//github.com/taraka1996/cart'
           sh 'ls -l'
        }      

        stage('compile/build') {
            sh 'env'
            common.compile()

        }
      

        stage('Test Cases') {
           common.testcases()
        }

        stage('code quality') {
            coomon.codequality()
        }

      } catch (e) {
           mail body: "<h1>${component} - Pipeline Failed \n ${BUILD_URL}</h1>", from: 'tarakatirumala@gmail.com', subject: "${component}- Pipeline Failed", to: 'tarakatirumala@gmail.com',  mimeType: 'text/html'
        }
                
            