# Wantify Deploy Automation Scripts

A set of simple bash scripts that can be used to deploy the latest artifacts representing the Wantify application to AWS.

## Setup Instuctions

Install the AWS CLI Tool (Command-line interface):

```
pip install awscli
```

An AWS admin should create you as a user within the AWS IAM service.  Once your account has been created, you can log into the AWS Console at https://152660121739.signin.aws.amazon.com/console .  Within the Console, you should select Services from the main menu bar, and type IAM to go to the IAM Management Console.  Within the IAM Management Console, you should select "Users" from the left-hand navigation pane.
You will be presented with a list of users.  Click on the user which corresponds to your own username to view your account summary. Within the account summary screen, select the "Security credentials" tab, and then click on the "Create access key" button.  A modal dialog should pop you which shows you your new "Access key id" and its corresponding "Secret access key".  Before closing this screen, copy both of these value to a new Secure Note named "Shrine AWS Access Keys" within LastPass. Close the screen. You should now see two entries in the "Access keys" section of the "Security credentials" tab: the one you just created and an older one that was automatically created for you. Delete the one with the older create date by clicking on the "x" button at the end of the table row. 

Back on your Mac terminal window, Configure the AWS CLI Tool to use your AWS credentials, and to set a default region:

Create a new file named ~/.aws/credentials and place the following contents in it:

```
[shrine]
aws_access_key_id = <YOUR_AWS_ACCESS_KEY_ID>
aws_secret_access_key = <YOUR_AWS_SECRET_ACCESS_KEY>
```

Create a new file named ~/.aws/config and place the following contents in it:

```
[profile shrine]
region = us-east-2
```
Edit your ~/.bash_profile to include the following lines and then source it (the value of the ARTIFACTORY_KEY variable can be found in the Notes section of the LastPass entry named "Shrinedev Artifactory - Jenkins"):

```
export AWS_PROFILE=shrine
export ARTIFACTORY_KEY=<KEY>
```

Install the Kubernetes client application:

```
brew install kubectl
```

Create a new file named ~/.kube/config and fill it with the contents of the Secure Note named "Shrine Kubernetes Config", which is stored in LastPass.

```cd``` to the ```dev``` directory of the ```wantify-deploy-automation``` project.

Make a copy of the ```migrations.env.example``` file named ```migrations.env``` and edit this new file, replacing the values of the DATABASE_USERNAME and DATABASE_PASSWORD properties with the values stored in a LastPass Secure Note named "Shrinedev Postgres - Dev".

Execute the following command to deploy the latest version of the development artifacts to the AWS Kubernetes cluster, and to execute the latest migrations against the development RDS instance:

```
./dev-deploy.sh
```

To view the results of the latest deployment to the development environment, you can access the wantify-server at http://dev.apiv2.wantify.com:8080 

You can also connect to the development PostgreSQL database by opening the LastPass SecureNote named "Shrinedev AWS SSH Key", following the instructions in the "Notes" field, and then executing the following command to establish an SSH tunnel to the development database:

```
ssh -N -L 5462:wantify-dev.cgcghsuzur6g.us-east-2.rds.amazonaws.com:5432 admin@api.dev.apiv2.wantify.com -i ~/.ssh/shrinedev-aws.pem
```

Once you have established this tunnel, you should be able to connect to the development PostgreSQL database with any Postgres client using the following parameters:

```
host: localhost
port: 5462
username: get from LastPass Secure Note named "Shrinedev Postgres - Dev"
password: get from LastPass Secure Note named "Shrinedev Postgres - Dev"
```



