Protptype Project
================
## Overview
### フォルダ構成
* adk : ADKを利用するAndroidアプリ（ライブラリとしてforMegaAdkLibを指定する）
* forMegaAdkLib : adkを使うAndroidアプリのライブラリ
* Arduino : Arduinoアプリ
* Node : Node.js学習用サンプルコード
* SampleSockeIO : express, ejs, Socket.IOを使ったチャットサンプル
* NodeServer : プロトタイプ作成用のサーバサイドとブラウザクライアント(/chat と /webcommand のエントリポイント)

### 動作確認済みバージョン
* Node.js : v0.8.7（nvm lsで確認可能）

        Macでv0.8系をインストールするにはXcodeが必要
* Socket.IO : v0.9（SampleSocketIOおよびNodeServerのディレクトリでnpm listで確認可能）
* express 3.0.0rc3

### Node.jsのインストール

#### nvm

```
$ git clone https://github.com/creationix/nvm.git .nvm
// .bashrcに以下を追加しておくとターミナル起動時にnvmが使える(Macの場合は.bash_profile)
source ~/.nvm/nvm.sh
// スクリプト実行
$ source .nvm/nvm.sh
// Node.jsをインストール
$ nvm install v0.8.16
$ nvm ls
$ nvm use v0.8.16
$ node --version

```

#### nodebrew
```
$ curl https://raw.github.com/hokaccha/nodebrew/master/nodebrew | perl - setup
// .bashrcに以下を追加しておくと起動時にnodeが使える(Macの場合は.bash_profile)
export PATH=$HOME/.nodebrew/current/bin:$PATH
// 読み直し
$ source .bashrc
// Node.jsをインストール
$ nodebrew install-binary v0.8.16
$ nodebrew ls
$ nodebrew use v0.8.16
$ node --version
```

