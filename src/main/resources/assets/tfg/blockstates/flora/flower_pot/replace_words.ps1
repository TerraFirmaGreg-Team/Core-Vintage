$directory = $PSScriptRoot
$files = Get-ChildItem -Path $directory -Recurse -File

foreach ($file in $files) {
  $content = Get-Content -Path $file.FullName -Raw
  $newContent = $content -replace '(\w+)\/\1_', '$1/'
  Set-Content -Path $file.FullName -Value $newContent -Encoding UTF8
}
