# Simple command line wrapper for Jib core libraries

## Setup
### Build
This needs to be done before the `cmdjib` command will work
```bash
./gradlew shadowJar
```

### Install
The following commands need to be executed as root or prefixed with sudo
```bash
cp cmdjib /usr/local/bin/
cp build/libs/cmdjib-1.0-SNAPSHOT-all.jar /usr/local/bin/
```

## Show help
```bash
cmdjib --help
```

## Examples

### Example 1 - Add single static file to an Nginx image
```bash
echo "<h1>Hello from jibcmd</h1>" > index.html
cmdjib --from nginxinc/nginx-unprivileged:stable-alpine --to tons/jibcmd --layer ./index.html /usr/share/nginx/html
docker run -it -p 8080:8080 tons/jibcmd
```

### Example 2 - Add Vue.js page to an Nginx image and run with the nginx user
```bash
vue create vue-app
cd vue-app
npm run build
cd ..
cmdjib --from nginxinc/nginx-unprivileged:stable-alpine --to tons/jibcmd --layer ./vue-app/dist /usr/share/nginx/html --user nginx
docker run -it -p 8080:8080 tons/jibcmd
```

### Example 3 - Add single static file to an Nginx image and push image to remote registry
```bash
echo "<h1>Hello from jibcmd</h1>" > index.html
cmdjib --from nginxinc/nginx-unprivileged:stable-alpine --to your.docker.registry.com/username/jibcmd --layer ./index.html /usr/share/nginx/html --reg-user your-registry-username --reg-pass your-registry-password
```

## Credits
* https://github.com/ajalt/clikt
* https://github.com/GoogleContainerTools/jib/tree/master/jib-core
