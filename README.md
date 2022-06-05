# firebase-auth

## アプリ概要

Firebaseの認証機能を実装するときにUIがFirebaseにどっぷり依存してしまうと聞いて、Presentation層をFirebaseに依存させずに実装してみたサンプルアプリです。
Domain層やPresentation層に対してはFirebaseを完全に隠蔽できていて、Data層のみがFirebaseを知っている設計になっています。
サインアップ画面、サインイン画面、ホーム画面のみ実装しています。

## 使用技術

- Firebase
- Dagger Hilt
- Coroutines
- StateFlow
- Jetpack Compose
- MockK