apiVersion: v1
kind: Service
metadata:
  name: wantify-keycloak
  annotations:
    dns.alpha.kubernetes.io/external: "id.dev.apiv2.wantify.com"
    service.beta.kubernetes.io/aws-load-balancer-ssl-cert: arn:aws:acm:us-east-2:152660121739:certificate/d4749730-fe0c-4b3e-9cac-8cca23b71ec4
    service.beta.kubernetes.io/aws-load-balancer-backend-protocol: http
spec:
  type: LoadBalancer
  selector:
    app: wantify-keycloak
  ports:
    - port: 443
      targetPort: 8080
---
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: wantify-keycloak
spec:
  replicas: 1
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1
  revisionHistoryLimit: 10
  template:
    metadata:
      labels:
        app: wantify-keycloak
    spec:
      containers:
        - name: wantify-keycloak
          image: "shrinedevelopment-docker-develop.jfrog.io/wantify-keycloak:{{version}}"
          imagePullPolicy: Always
          resources:
            limits:
              memory: "1200Mi"
            requests:
              memory: "900Mi"
              cpu: "200m"
          ports:
            - name: http
              containerPort: 8080
          env:
            - name: PROXY_ADDRESS_FORWARDING
              value: "true"

            - name: POSTGRES_ADDR
              value: "postgres"

            - name: POSTGRES_PORT
              value: "5432"
     
            - name: POSTGRES_DATABASE
              value: "wantify_dev"

            - name: POSTGRES_USER
              valueFrom:
                secretKeyRef:
                  name: db-user-pass
                  key: username

            - name: POSTGRES_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: db-user-pass
                  key: password

      imagePullSecrets:
        - name: "regcreds"
