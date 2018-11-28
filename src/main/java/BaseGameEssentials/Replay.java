package BaseGameEssentials;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.*;

public class Replay {
    private static AWSCredentials credentials = new BasicAWSCredentials("", "");
    private static AmazonS3 s3client = new AmazonS3Client(credentials);
    private static File currentDir = new File(".");
    private static File parentDir = currentDir.getAbsoluteFile();
    private static File newFile = new File(parentDir + "/src/main/resources/replayDocument");
    private static FileWriter fr;
    private static BufferedWriter br;

    public static void initialize(){
        try {
            fr = new FileWriter(newFile);
            br = new BufferedWriter(fr);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //Takes String value of BaseGameEssentials.Player decision and records to replayDocument through BufferedWriter
    public static void recordAction(String input){
        try{
            br.write(input + "\n");
        }catch(Exception e){
            //DO THIS
            e.printStackTrace();
        }
    }

    //Closes BufferedWriter and calls uploadFile to send new replay to Amazon S3 Bucket
    public static void stopReplay() {
        try {
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        String bucketName = "risk-playback";
//        s3client.createBucket(bucketName);                              //  Creation of bucket unnecessary if bucket is manually created in advance

            try {
                createFolder(bucketName,"riskGame",s3client);
                uploadFile(bucketName, "riskGame");
            } catch (Exception x) {
                x.printStackTrace();
            }
        
    }

    //Uploads replayDocument to previously created folder in Bucket
    private static void uploadFile(String bucketName, String folderName){
        String fileName = folderName + "/" + "replayDocument.txt";
        s3client.putObject(new PutObjectRequest(bucketName, fileName, new File("C:\\Users\\capar\\Desktop\\Java Projects\\RiskGameTeam\\src\\main\\resources\\replayDocument")));
    }

//Meant for setup of folder without manual creation
    public static void createFolder(String bucketName, String folderName, AmazonS3 client){
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);

        // create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

        // create a PutObjectRequest passing the folder name suffixed by /
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + "/", emptyContent, metadata);

        // send request to S3 to create folder
        client.putObject(putObjectRequest);
    }

}