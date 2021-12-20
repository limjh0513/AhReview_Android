package com.hackathon.ahreview.data.model.response

// FIXME: 지금 response 모델이 수없이 많은데 이러면 나중에 프로젝트가 커지면 데이터 클래스가 너무 많아짐
// FIXME: 모두 API에 연결된 하나의 data 클래스들일텐데 이럴 경우에는 Response 클래스 하나에 data 클래스를 중첩으로 해서 관리하는 것이 좋음
// FIXME: 아래와 같이 이렇게 관리를 하면 하나의 파일 안에서 Response 모델을 효율적으로 관리할 수 있다

/**
 *  data class Response(
 *      val msg: String,
 *      val data: Data,
 *      val status: Int
 *  ) {
 *      data class Data(
 *          val totalCount: Int,
 *          val productList: List<Product>
 *      ) {
 *          data class Product(
 *              val id: Int,
 *              val name: String
 *          )
 *      }
 *  }
 */



data class Confidence(
    val negative: Double,
    val neutral: Double,
    val positive: Double
)