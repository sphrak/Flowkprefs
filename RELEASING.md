# RELEASING
The releasing procedure is fairly simple:

### Steps

- Add recent changes to [CHANGELOG](CHANGELOG.md) 
- Adjust [README](README.md) to reflect latest version
- `git tag -a version -m version`
- Push it remote and this will trigger a build
