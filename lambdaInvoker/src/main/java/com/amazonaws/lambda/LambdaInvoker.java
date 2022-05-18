package com.amazonaws.lambda;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.amazonaws.lambda.thirdparty.org.json.JSONObject;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;






public class LambdaInvoker implements RequestHandler<APIGatewayProxyRequest, APIGatewayProxyResponse> {

	APIGatewayProxyResponse response = new APIGatewayProxyResponse();

	@Override
	public APIGatewayProxyResponse handleRequest(APIGatewayProxyRequest input, Context context) {
		context.getLogger().log("Input: " + input);
		JSONObject pay = new JSONObject();
		pay.put("supervisor_employee_id_l1", "H123000");
		String s = pay.toString();
		
		
		try {
			LambdaClient awsLambda = LambdaClient.builder().build();
			context.getLogger().log("lambda client created !");

			JSONObject json = new JSONObject();
			json.put("supervisor_employee_id_l1", "H123000");
			SdkBytes payload = SdkBytes.fromUtf8String(json.toString());


			InvokeRequest request = InvokeRequest.builder()
					.functionName("arn:aws:lambda:ap-south-1:592866517209:function:GetMySupervisorNames").payload(payload).build();
					
			InvokeResponse res = awsLambda.invoke(request);
			context.getLogger().log("invoke request successfull !");
			String value = res.payload().asUtf8String();
			context.getLogger().log("response paylod created !  " + res.payload());

			response.setBody(value);
			response.setStatusCode(200);
			return response;
			
			
		} catch (Exception f) {
			context.getLogger().log("Exception occured :-  " + f.getMessage());
			JSONObject output = new JSONObject();
			output.put("message", "Exception occured :-  " + f.getMessage());

			response.setBody(output.toString());
			response.setStatusCode(400);
			return response;

		}

	}

}
