This tool shows the buy/sell price of BTC and ETH in USD

It makes recommendation on which platfrom to use for buying and selling depending on the most recent price

Tech used:

- Backend: Java Spring
- Fronend: Thymeleaf for HTML template
- Built with Maven

To obtain the application you can just download the jar file with the link below or pull the repo and build it yourself

Download for jar file: https://drive.google.com/file/d/1NfvNp9QXoAF0v7qLMh4qxCsbQquXuWxy/view?usp=sharing

Build the project by yourself
- pull the repo and open with an IDE
- in the terminal, enter command "mvn package"
- after the build process, a "target" folder will be created under the project directory
- inside "target" folder is where you will find the jar file Maven just built for you

To use the application, simply open the jar file, however, this application is just a background process, so you will not able to directly access it through an inteface.

You can open the task manager (Windows OS) to check if there is a background process called "Java(TM) Platform SE binary", if you found the process then it means the application is opened.

Once you made sure the application is running, open your web browser and enter "localhost:8080" in the URL field and press enter

Now you should see the application. You can refresh the page to see updated values

To close the application, you have to go to task manager and manually terminate the process.



Questionnaire:

Are there any sub-optimal choices( or short cuts taken due to limited time ) in your implementation?
- This tool doesn't really have any practical use, the application follows a simple MVC design which I am familiar with
- The rate fetched from Kraken platform doesn't seems to be correct, I am not familiar with how exactly the rates are used

Is any part of it over-designed? ( It is fine to over-design to showcase your skills as long as you are clear about it)
- No

If you have to scale your solution to 100 users/second traffic what changes would you make, if any?
- No

What are some other enhancements you would have made, if you had more time to do this implementation
- Automatically update the values every few seconds to always show the most recent rates
- I usually don't work in frontend, so, if I had the time, I would try to learn frontend stack to write better looking HTML pages
