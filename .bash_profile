# homebrew
export M2_HOME=/Users/tshutty/apache-maven-3.6.1
export PATH=$PATH:/usr/local/bin:~/bin:/usr/bin/python:/usr/local/bin/git:/usr/local/Cellar/ant/1.10.5/bin:$M2_HOME/bin:/Users/tshutty/javapractice/glassfish5/bin
alias showFiles='defaults write com.apple.finder AppleShowAllFiles YES; killall Finder /System/Library/CoreServices/Finder.app'
alias hideFiles='defaults write com.apple.finder AppleShowAllFiles NO; killall Finder /System/Library/CoreServices/Finder.app'
export JETTY_HOME=/usr/local/Cellar/jetty/9.4.14.v20181114/libexec
export JAVA_HOME=$(/usr/libexec/java_home)
export CATALINA_HOME=/Users/tshutty/apache-tomcat-7.0.93
export CATALINA_BASE=/Users/tshutty/apache-tomcat-7.0.93
stty -ixon
HISTSIZE=
HISTFILESIZE=
export JRE_HOME=/Library/Java/JavaVirtualMachines/jdk-12.0.2.jdk/Contents/Home/jre
export PATH="$(brew --prefix coreutils)/libexec/gnubin:/usr/local/bin:$PATH"
export CLICOLOR=1
export LSCOLORS=GxFxCxDxBxegedabagaced
export TERM="xterm-color"
export PS1='\[\e[0;33m\]\u\[\e[0m\]@\[\e[0;32m\]\h\[\e[0m\]:\[\e[0;36m\]\w\[\e[0m\]\$ '
function tyehello(){
    echo tye says $1
}
function gitrepo(){
    git clone git@github.com:tye-shutty/$1.git &&
    cd $1 &&
    git remote add upstream git@github.com:SerenovaLLC/$1.git &&
    git remote -v &&
    cd -
}
function masterrebase(){
    git branch -l &&
    git fetch upstream &&
    git diff remotes/upstream/master master &&
    git checkout master &&
    git rebase upstream/master &&
    echo New Diff: && 
    git diff remotes/upstream/master master
}
function nch(){
    for i in $(ls -d */); do
        cd i &&
        git remote set-url origin git@github.com:tye-shutty/${i%%/}.git &&
        git remote -v &&
        cd -
    done
}
function rebaseall(){
    for i in $(ls -d */); do
        cd i &&
        masterrebase &&
        cd -
    done
}
function xf(){
    find "$1" -iname "$2" -prune -o -iname "$3" 2>&1 | grep -Ev "(Not a directory|Permission denied|Operation not permitted)"
}
function rf(){
	find . -depth 1 -name "$1" 2> /dev/null 
}
function shrink(){
    OIFS="$IFS"
    IFS=$'\n'
    for i in $(find . -maxdepth 1 -type f | grep "pdf"); do
        x=$(echo $i | sed  -e 's:./::g')
        y=$(echo $x | sed -e 's:.pdf::g')
        echo $x
        gs -sDEVICE=pdfwrite -dCompatibilityLevel=1.4 -dPDFSETTINGS=/screen -dNOPAUSE -dQUIET -dBATCH -sOutputFile=$y-opt.pdf $x
    done
    IFS="$OIFS"
}
function prefix(){
    OIFS="$IFS"
    IFS=$'\n'
    for i in `find . -regex \./[^\.].*`
    do
        y=$(echo $i | sed -e 's:./::g')
        x=$(echo $y | tr \  - | tr -s '-')
        echo $1-$x
        mv "$y" $1-$x
    done
    IFS="$OIFS"
}

function dcolor(){
    mkdir new
    OIFS="$IFS"
    IFS=$'\n'
    for i in $(find . -maxdepth 1 -type f | grep -E '\.png|\.gif|\.jpg'); do
        x=$(echo $i | sed  -e 's:./::g')
        y=$(echo $x | sed -e 's:\..*::g')
        echo $x
        magick $x -resample 25x25 new/$y-opt.jpg
    done
    IFS="$OIFS"
}
function dblack(){
    mkdir new
    OIFS="$IFS"
    IFS=$'\n'
    for i in $(find . -maxdepth 1 -type f | grep -E '\.png|\.gif|\.jpg'); do
        x=$(echo $i | sed  -e 's:./::g')
        y=$(echo $x | sed -e 's:\..*::g')
        echo $x
        magick $x -resample 18x18 -monochrome new/$y-opt.gif
    done
    IFS="$OIFS"
}
function dgray(){
    mkdir new
    OIFS="$IFS"
    IFS=$'\n'
    for i in $(find . -maxdepth 1 -type f | grep -E '\.png|\.gif|\.jpg'); do
        x=$(echo $i | sed  -e 's:./::g')
        y=$(echo $x | sed -e 's:\..*::g')
        echo $x
        magick $x -resample 18x18 -colorspace gray new/$y-opt.jpg
    done
    IFS="$OIFS"
}

