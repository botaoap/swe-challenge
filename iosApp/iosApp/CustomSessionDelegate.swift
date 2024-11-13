//
//  CustomSessionDelegate.swift
//  iosApp
//
//  Created by Gabriel Pereira on 12/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

@objc class CustomSessionDelegate: NSObject, URLSessionDelegate {
    @objc func urlSession(_ session: URLSession, didReceive challenge: URLAuthenticationChallenge, completionHandler: @escaping (URLSession.AuthChallengeDisposition, URLCredential?) -> Void) {
        completionHandler(.useCredential, URLCredential(trust: challenge.protectionSpace.serverTrust!))
    }
}
