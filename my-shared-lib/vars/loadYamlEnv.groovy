#!/usr/bin/env groovy
@Grab('org.yaml:snakeyaml:1.33')
import org.yaml.snakeyaml.Yaml

def call(String filePath, String envName) {
    // Read file content from workspace (agent)
    def yamlText = readFile(filePath)  // readFile is a Jenkins pipeline step

    if (!yamlText) {
        error "YAML file not found or empty: ${filePath}"
    }

    def yaml = new Yaml()
    def data = yaml.load(yamlText)

    if (!data.containsKey(envName)) {
        error "Environment '${envName}' not found in YAML file"
    }

    def envData = data[envName]
    echo "Loading environment variables for '${envName}': ${envData}"

    // Set environment variables in pipeline
    envData.each { key, value ->
        env."${key}" = value.toString()
        echo "Set ${key}=${value}"
    }

    // Optional: return the map for further use
    return envData
}
