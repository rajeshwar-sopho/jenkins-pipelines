#!/usr/bin/env groovy
@Grab('org.yaml:snakeyaml:1.33')
import org.yaml.snakeyaml.Yaml

def call(String filePath, String envName) {
    // Load YAML content
    def yamlFile = new File(filePath)
    if (!yamlFile.exists()) {
        error "YAML file not found: ${filePath}"
    }

    def yaml = new Yaml()
    def data = yaml.load(yamlFile.text)

    if (!data.containsKey(envName)) {
        error "Environment '${envName}' not found in YAML file"
    }

    def envData = data[envName]
    echo "Loading environment variables for ${envName}: ${envData}"

    // Set environment variables in pipeline
    envData.each { key, value ->
        env."${key}" = value.toString()
        echo "Set ${key}=${value}"
    }
}
