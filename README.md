# Java hosts Minifier<br>
This is a Java program that downloads, minifies, checks for duplicate entries and correctly places a host file from a URL.
Works only in Windows, needs to be run on the same disk as your hosts file and requires admin rights (because of the location of the hosts file).<br><br>
Usage: (compile and) run the java program. Before running, pass the location of your hosts file (if you're on windows, just input "windows" instead) and the URL from where to download.<br>

Examples:<br>
- `windows https://someonewhocares.org/hosts/zero/hosts`<br>
- `C:\Windows\System32\drivers\etc\hosts https://raw.githubusercontent.com/StevenBlack/hosts/master/hosts`<br><br>

Host file providers I like: someonewhocares.org, EasyList, AdGuard, StevenBlack Hosts
