import { Component, Input, OnInit } from '@angular/core';
import { NbToastrService } from '@nebular/theme';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import * as moment from 'moment';
import { Color, Label } from 'ng2-charts';
import { ReportsService } from 'src/app/service/reports.service';
import { SprintService } from 'src/app/service/sprint.service';
import { StateService } from 'src/app/service/state.service';
import { DatePipe } from '@angular/common';
import { reduce } from 'rxjs/operators';



@Component({
  selector: 'app-risk-analysis',
  templateUrl: './risk-analysis.component.html',
  styleUrls: ['./risk-analysis.component.css'],
  providers: [DatePipe]

})
export class RiskAnalysisComponent implements OnInit {

  date: Date
  avgSt: number;
  total: number;
  completedSt:number;
  mediumRisk: string;
  lowRisk: string;
  totalTasks: number;
  completedTasks: number;
  inprogressTasks: number;
  openTasks: number;
  totalIssues: number;
  completedIssues: number;
  inprogressIssues: number;
  openIssues: number;
  momentParser = moment;
  todo: any;
  inprogress: any;
  completed: any;
  chartData: number;
  activeSprintDetails: any;
  sprintID: any;
  projectID: any;
  riskChartData: any = [];
  taskChartData: any = [] ;
  issueChartData: any = [] ;
  donutChart1Data: ChartDataSets[] = [ 
    {
      data: [],
      label: 'Tasks',
    }
  ];
  donutChart2Data: ChartDataSets[] = [
    {
      data: [],
      label: 'Issues',

    },
  ];
  barChartData: ChartDataSets[] = [
    {
      data: [],
      label: 'Todo',

    },
    {
      data: [],
      label: 'In progress',

    },
    {
      data: [],
      label: 'Completed',

    },
  ];
  barChartLabels: Label[] = [];
  donutChart1Labels: Label[] = [];
  donutChart2Labels: Label[] = [];
  barChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [{
        ticks: {
          beginAtZero: true
        }
      }]
    }
  };
  barChartLegend = true;
  barChartType: ChartType = 'bar';
  donutChart1Type: ChartType = 'doughnut';
  donutChart2Type: ChartType = 'doughnut';
  barChartPlugins = [];
  barChartColors: Color[] = [
    {
      backgroundColor: 'rgba(230, 48, 48)',

    },
    {
      backgroundColor: 'rgba(230, 230, 48)',

    },
    {
      backgroundColor: 'rgba(8, 161, 0)',
    }

  ];

  donutChart1Colors: Color[] = [
    {
      backgroundColor: [
        'rgb(230, 48, 48)',
        'rgb(230, 230, 48)',
        'rgb(8, 161, 0)'
      ],
      
    },


  ];

  donutChart2Colors: Color[] = [
    {
      backgroundColor: [
        'rgb(230, 48, 48)',
        'rgb(230, 230, 48)',
        'rgb(8, 161, 0)'
      ],
      
    },


  ];
  risk: string;
  displaySuccess: string;
  displaymedium: string;
  displaymediumSuccess: string;
  displaydanger: string;
  successRate: number;
  mediumRate: number;
  dangerRate: number;

  constructor(
    private reportsService: ReportsService,
    private sprintService: SprintService,
    private stateService: StateService,
    private toastrService: NbToastrService,
    private datePipe: DatePipe
  ) { }

  ngOnInit() {
    this.stateService.selectedProjectId.subscribe((data) => {
      this.projectID = Number(data);
      this.sprintService.getActiveSprintByProject(this.projectID).subscribe((activeSprintData) => {
        console.log(activeSprintData)
        if (activeSprintData && activeSprintData[0]) {
          this.activeSprintDetails = activeSprintData;
          this.sprintService.getProgressbar(activeSprintData[0].id).subscribe((progressData) => {
            this.total = progressData.todo + progressData.inprogress + progressData.completed;
            this.completedSt = progressData.completed;
            this.avgSt = this.total / 14;

          });
          this.sprintService.getCompletedTaskCount(activeSprintData[0].id).subscribe((completeData) => {
            console.log(completeData)
            this.completedTasks = completeData.completed;
            this.inprogressTasks = completeData.inprogress;
            this.openTasks = completeData.todo;
            this.totalTasks = this.completedTasks + this.inprogressTasks + this.openTasks;
          });

          this.sprintService.getCompletedIssueCount(activeSprintData[0].id).subscribe((issueData) => {
            console.log(issueData)
            this.completedIssues = issueData.completed;
            this.inprogressIssues = issueData.inprogress;
            this.openIssues = issueData.todo;
            console.log(this.completedIssues, this.inprogressIssues, this.openIssues)

            this.totalIssues = this.completedIssues + this.inprogressIssues + this.openIssues;

          });
        }
      })
    });

    this.stateService.selectedProjectId.subscribe((data) => {
      this.projectID = Number(data);
      if (this.projectID !== 0) {

        this.sprintService.getActiveSprintByProject(this.projectID).subscribe((activeSprint) => {
          if (activeSprint && activeSprint[0]) {
            this.activeSprintDetails = activeSprint;
            this.sprintService.getProgressbar(activeSprint[0].id).subscribe((progressData) => {
              if (progressData) {
                this.riskChartData.push(progressData)
                console.log(this.riskChartData[0]);
                if (this.riskChartData.length !== 0) {
                  this.riskChartData.forEach((element) => {
                    if (element) {
                      this.barChartData[0].data.push(element.todo);
                      this.barChartData[1].data.push(element.inprogress);
                      this.barChartData[2].data.push(element.completed);
                      this.barChartLabels.push("Story points");

                    }

                  });
                }

              }
            });

            this.sprintService.getCompletedTaskCount(activeSprint[0].id).subscribe((TaskData) => {
              if (TaskData) {
                this.taskChartData.push(TaskData)
                console.log(this.taskChartData[0]);
                if (this.taskChartData.length !== 0) {
                  this.taskChartData.forEach((element) => {
                    if (element) {
                      this.donutChart1Data[0].data.push(element.todo);
                      this.donutChart1Data[0].data.push(element.inprogress);
                      this.donutChart1Data[0].data.push(element.completed);
                      console.log(this.donutChart1Data[0].data)
                      this.donutChart1Labels.push("Open");
                      this.donutChart1Labels.push("In progress");
                      this.donutChart1Labels.push("Completed");

                    }

                  });
                }

              }
            });

            this.sprintService.getCompletedIssueCount(activeSprint[0].id).subscribe((IssueData) => {
              if (IssueData) {
                this.issueChartData.push(IssueData)
                console.log(this.issueChartData[0]);
                if (this.issueChartData.length !== 0) {
                  this.issueChartData.forEach((element) => {
                    if (element) {
                      this.donutChart2Data[0].data.push(element.todo);
                      this.donutChart2Data[0].data.push(element.inprogress);
                      this.donutChart2Data[0].data.push(element.completed);
                      console.log(this.donutChart2Data[0].data)
                      this.donutChart2Labels.push("Open");
                      this.donutChart2Labels.push("In progress");
                      this.donutChart2Labels.push("Completed");

                    }

                  });
                }

              }
            });

          }
        })
      }
    });
    this.date = new Date();
  }
  chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
  }

  calculateDiff() {
    var today = moment().format('YYYY-MM-DD');
    var sprintdate = moment(this.activeSprintDetails[0].endAt);
    var currentdate = moment(today);
    var result = sprintdate.diff(currentdate, 'days');
    return result;
  }

  calculateRisk() {
    var today = moment().format('YYYY-MM-DD');
    var sprintdate = moment(this.activeSprintDetails[0].endAt);
    var currentdate = moment(today);
    var remaingDays = sprintdate.diff(currentdate, 'days');

  this.displaySuccess = "Well done..your ahead of your timeline!";
  this.displaymediumSuccess = "You are on track..keep the pace!"
  this.displaymedium = "Pick up the pace..you might run short of time!"
  this.displaydanger = "At a high risk!!!..You might lose time";
  this.successRate = this.total * 75 / 100;
  this.mediumRate = this.total * 50 / 100;
  this.dangerRate = this.total * 25 / 100;
  
      if (this.completedSt <= this.dangerRate) {
         this.risk = this.displaydanger;
      }
      else if(this.completedSt <= this.mediumRate && this.completedSt >= this.dangerRate){
        this.risk = this.displaymediumSuccess;
      }
      else if(this.completedSt <= this.successRate && this.completedSt >= this.mediumRate){
        this.risk = this.displaymedium;
      }
      else if(this.completedSt <= this.mediumRate){
        this.risk = this.displaySuccess;
      }
      
      return  this.risk; 
    


  }
}

