Helm Configuration

Create a folder helm-charts in the root directory of the project.
Create a folder discovery-service in the helm-charts directory.
helm create discovery-service
This will create a basic Helm chart structure for the discovery-service.
The structure will look like this:
```
discovery-service/
  ├── Chart.yaml
  ├── values.yaml
  ├── charts/
  ├── templates/
  │   ├── deployment.yaml
  │   ├── service.yaml
  │   └── ingress.yaml
```
```