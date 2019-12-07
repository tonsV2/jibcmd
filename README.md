# Simple command line wrapper for Jib core libraries

## Show help
```bash
./gradlew run --args='--help'
```

## Example 1 - Add single static file to an Nginx image
```bash
echo "<h1>Hello from jibcmd</h1>" > index.html
./gradlew run --args='--from nginxinc/nginx-unprivileged --to tons/jibcmd --layer ./index.html /usr/share/nginx/html'
docker run -it -p 8080:8080 tons/jibcmd
```

## Example 2 - Add Vue.js page to an Nginx image
```bash
vue create vue-app
cd vue-app
npm run build
cd ..
./gradlew run --args='--from nginxinc/nginx-unprivileged --to tons/jibcmd --layer ./vue-app/dist /usr/share/nginx/html'
docker run -it -p 8080:8080 tons/jibcmd
```

## Credits
* https://github.com/ajalt/clikt
* https://github.com/GoogleContainerTools/jib/tree/master/jib-core
