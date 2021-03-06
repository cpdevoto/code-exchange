apiVersion: v1
kind: Service
metadata:
  name: wantify-server
  annotations:
    dns.alpha.kubernetes.io/external: "dev.apiv2.wantify.com"
spec:
  type: LoadBalancer
  selector:
    app: wantify-server
  ports:
    - protocol: TCP
      port: 8080
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
                  name: db-user-pass
                  key: username

            - name: DATABASE_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: db-user-pass
                  key: password

            - name: JAVA_OPTS
              value: "-Xmx768m -Xms768m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/dump/"

            - name: WANTIFY_VERSION
              value: "1.0.0"
              
      imagePullSecrets:
        - name: "regcreds"
