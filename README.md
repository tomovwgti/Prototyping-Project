Protptype Project
================
## developブランチOverview
### フォルダ構成
* adk : ADKを利用するAndroidアプリ（ライブラリとしてforMegaAdkLibを指定する）
* forMegaAdkLib : adkを使うAndroidアプリのライブラリ
* Arduino : Arduinoアプリ
* Node : Node.js学習用サンプルコード
* sampleSockeIO : express, ejs, Socket.IOを使ったチャットサンプル
* NodeServer : プロトタイプ作成用のサーバサイドとブラウザクライアント
 /chat と /webcommand のエントリポイント
### 動作確認済みバージョン
* Node.js : v0.8.7（nvm lsで確認可能）
* Socket.IO : v0.9（SampleSocketIOおよびNodeServerのディレクトリでnpm listで確認可能）
* express 3.0.0rc3