def call(String appName) {
    echo "Building app ${appName}"
    sh "make ${appName}"
}
