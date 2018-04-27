apiVersion: v1
kind: Service
metadata:
  name: wantify-server
  annotations:
    dns.alpha.kubernetes.io/external: "dev.apiv2.wantify.com"
    service.beta.kubernetes.io/aws-load-balancer-ssl-cert: arn:aws:acm:us-east-2:152660121739:certificate/d4749730-fe0c-4b3e-9cac-8cca23b71ec4
    service.beta.kubernetes.io/aws-load-balancer-backend-protocol: http
spec:
  type: LoadBalancer
  selector:
    app: wantify-server
  ports:
    - port: 443
      targetPort: 8080
---
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: wantify-server
spec:
  replicas: 2
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 3
  revisionHistoryLimit: 10
  template:
    metadata:
      labels:
        app: wantify-server
    spec:
      containers:
        - name: wantify-server
          image: "shrinedevelopment-docker-develop.jfrog.io/wantify-server:{{version}}"
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
            - name: DATABASE_HOST
              value: "postgres"

            - name: DATABASE_PORT
              value: "5432"
     
            - name: DATABASE_NAME
              value: "wantify_dev"

            - name: DATABASE_USERNAME
              valueFrom:
                secretKeyRef:
                  name: server-db-user-pass
                  key: username

            - name: DATABASE_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: server-db-user-pass
                  key: password

            - name: KEYCLOAK_ISSUER
              value: https://id.dev.apiv2.wantify.com/auth/realms/wantify

            - name: KEYCLOAK_AUDIENCE
              value: wantify-server

            - name: KEYCLOAK_APP_AUDIENCE
              value: wantify-app

            - name: KEYCLOAK_KEYS_DEFAULT
              value: '{"keys":[{"kid":"Hd7YXXMRZu38A1EPVAeTUkDLEkGwa8S3toZb9Bdz42Y","kty":"RSA","alg":"RS256","use":"sig","n":"uEJ1l9fIPGCS-7bqdx1IM5hk_DoruF-_i-AUYPQo-AlEwIfRXW0oOFD4_ohwE1_PuO7LBJxjlOrurSVN5TcXBmoLvg7WaUlw7e9TXNy717Iu57DKcRYRddEYdQoYTiLF_q5p2DCXmtMxtF7_h4D9OiAicUUapAn8J6s1E4UAtJeO2UMBQxAIyJumS5Av35ewLa60nhNFvBuIFb50LBdFrIKytQ52loAxtjwsdq8WBzOSg3xEtiRbooDtUq1viYdQ5G0hYR4--xU8XIlLQHDsdb0_Psk6FWh5g6Csp7-IhOJKVcvZXPADS4fgi_v22tPj2aIODrtVaqMUBHvxlfcBjw","e":"AQAB"}]}'

            - name: JAVA_OPTS
              value: "-Xmx768m -Xms768m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/dump/"

            - name: WANTIFY_VERSION
              value: "1.0.0"
              
      imagePullSecrets:
        - name: "regcreds"
