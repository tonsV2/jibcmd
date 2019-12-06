# Simple command line wrapper for Jib core libraries

## Show help
```bash
./gradlew run --args='--help'
```

## Example
```bash
echo "<h1>Hello from jibcmd</h1>" > index.html
./gradlew run --args='--from nginxinc/nginx-unprivileged --to tons/jibcmd --layer ./index.html /usr/share/nginx/html'
docker run -it -p 8080:8080 tons/jibcmd
```

## Credits
* https://github.com/ajalt/clikt
* https://github.com/GoogleContainerTools/jib/tree/master/jib-core
