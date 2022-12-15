fileName="versions.js"
fileStart="let versions = [ "
fileEnd=" ];"
versions=( $(ls -d v*/) )
stringVersions=""
for version in ${versions[@]}
do
stringVersions="${stringVersions}'${version:0:${#version} - 1}', "
done
if [ "$stringVersions" = "" ];
then
    fileContent="${fileStart}${fileEnd}"
else
    fileContent="${fileStart}${stringVersions:0:${#stringVersions} - 2}${fileEnd}"
fi
echo ${fileContent} > ${fileName}
